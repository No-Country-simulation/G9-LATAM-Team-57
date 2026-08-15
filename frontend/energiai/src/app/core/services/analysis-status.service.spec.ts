import { TestBed } from '@angular/core/testing';

import { AnalysisStatusService } from './analysis-status.service';

describe('AnalysisStatusService', () => {
  let service: AnalysisStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalysisStatusService);
  });

  it('starts in idle state', () => {
    expect(service.status()).toBe('idle');
  });

  it('marks conectado', () => {
    service.markConectado();
    expect(service.status()).toBe('conectado');
  });

  it('marks fallback', () => {
    service.markFallback();
    expect(service.status()).toBe('fallback');
  });

  it('resets to idle', () => {
    service.markFallback();
    service.reset();
    expect(service.status()).toBe('idle');
  });
});
