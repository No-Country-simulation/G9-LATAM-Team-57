import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError, TimeoutError } from 'rxjs';

import { NotificationService } from '../services/notification.service';

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
          const message = httpError.error?.message || 'Los datos enviados no son válidos.';
          notification.showError('Error de validación', message);
          break;
        }

        case 404:
          notification.showError(
            'Servicio no encontrado',
            'El servicio solicitado no está disponible.'
          );
          break;

        case 500:
          notification.showError(
            'Error interno',
            'Ocurrió un error en el servidor. Intente nuevamente más tarde.'
          );
          break;

        case 503:
          notification.showError(
            'Servicio no disponible',
            'El servicio se encuentra temporalmente no disponible. Intente en unos minutos.'
          );
          break;

        default:
          notification.showError(
            'Error inesperado',
            'Ocurrió un error inesperado. Intente nuevamente.'
          );
          break;
      }

      return throwError(() => httpError);
    })
  );
};
