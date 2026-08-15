import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError, TimeoutError } from 'rxjs';

import { NotificationService } from '../services/notification.service';

/**
 * Extrae un mensaje legible del cuerpo de error que devuelve el backend.
 * El backend puede responder con `{message}`, `{error}` o un mapa `{campo: mensaje}`.
 */
function extractMessage(httpError: HttpErrorResponse, fallback: string): string {
  const body = httpError.error;

  if (!body) {
    return fallback;
  }

  if (typeof body === 'string') {
    return body;
  }

  if (typeof body.message === 'string' && body.message) {
    return body.message;
  }

  if (typeof body.error === 'string' && body.error) {
    return body.error;
  }

  const messages = Object.values(body)
    .filter((value): value is string => typeof value === 'string' && value.length > 0)
    .map((value) => value)
    .join(' ');

  return messages || fallback;
}

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const notification = inject(NotificationService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse | TimeoutError) => {
      if (error instanceof TimeoutError) {
        notification.showError(
          'Error de conexión',
          'El servidor no respondió a tiempo. Verifique su conexión e intente nuevamente.'
        );
        return throwError(() => new Error('Timeout: El servidor no respondió'));
      }

      const httpError = error as HttpErrorResponse;

      switch (httpError.status) {
        case 400: {
          notification.showError(
            'Error de validación',
            extractMessage(httpError, 'Los datos enviados no son válidos.')
          );
          break;
        }

        case 404:
          notification.showError(
            'Servicio no encontrado',
            extractMessage(httpError, 'El servicio solicitado no está disponible.')
          );
          break;

        case 500:
          notification.showError(
            'Error interno',
            extractMessage(
              httpError,
              'Ocurrió un error en el servidor. Intente nuevamente más tarde.'
            )
          );
          break;

        case 502:
          notification.showError(
            'Error de comunicación',
            extractMessage(
              httpError,
              'El servicio no pudo completar la solicitud. Intente nuevamente.'
            )
          );
          break;

        case 503:
          notification.showError(
            'Servicio no disponible',
            extractMessage(
              httpError,
              'El servicio se encuentra temporalmente no disponible. Intente en unos minutos.'
            )
          );
          break;

        default:
          notification.showError(
            'Error inesperado',
            extractMessage(httpError, 'Ocurrió un error inesperado. Intente nuevamente.')
          );
          break;
      }

      return throwError(() => httpError);
    })
  );
};
