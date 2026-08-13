import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { NotificationService } from '../services/notification.service';
import { httpErrorInterceptor } from './http-error.interceptor';

describe('httpErrorInterceptor', () => {
  let http: HttpClient;
  let httpMock: HttpTestingController;
  let notification: { showError: ReturnType<typeof vi.fn> };

  beforeEach(() => {
    notification = { showError: vi.fn() };

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([httpErrorInterceptor])),
        provideHttpClientTesting(),
        { provide: NotificationService, useValue: notification },
      ],
    });

    http = TestBed.inject(HttpClient);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('extracts a field message from validation errors', () => {
    http.post('/analisis-energetico', {}).subscribe({ error: () => undefined });

    const req = httpMock.expectOne('/analisis-energetico');
    req.flush(
      { householdSize: 'Debe ser mayor que 0' },
      { status: 400, statusText: 'Bad Request' }
    );

    expect(notification.showError).toHaveBeenCalledWith(
      'Error de validación',
      'Debe ser mayor que 0'
    );
  });

  it('extracts a message from an {error} body', () => {
    http.post('/analisis-energetico', {}).subscribe({ error: () => undefined });

    const req = httpMock.expectOne('/analisis-energetico');
    req.flush({ error: 'Solicitud invalida.' }, { status: 400, statusText: 'Bad Request' });

    expect(notification.showError).toHaveBeenCalledWith(
      'Error de validación',
      'Solicitud invalida.'
    );
  });

  it('falls back to a default message when the body has no message', () => {
    http.post('/analisis-energetico', {}).subscribe({ error: () => undefined });

    const req = httpMock.expectOne('/analisis-energetico');
    req.flush({}, { status: 500, statusText: 'Internal Server Error' });

    expect(notification.showError).toHaveBeenCalledWith(
      'Error interno',
      'Ocurrió un error en el servidor. Intente nuevamente más tarde.'
    );
  });
});
