import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  showSuccess(title: string, message: string): void {
    Swal.fire({
      icon: 'success',
      title,
      text: message,
      confirmButtonColor: '#2E7D32',
      timer: 4000,
      timerProgressBar: true,
    });
  }

  showError(title: string, message: string): void {
    Swal.fire({
      icon: 'error',
      title,
      text: message,
      confirmButtonColor: '#F44336',
    });
  }

  showWarning(title: string, message: string): void {
    Swal.fire({
      icon: 'warning',
      title,
      text: message,
      confirmButtonColor: '#FF9800',
    });
  }

  showInfo(title: string, message: string): void {
    Swal.fire({
      icon: 'info',
      title,
      text: message,
      confirmButtonColor: '#2196F3',
    });
  }

  async showConfirm(title: string, message: string): Promise<boolean> {
    const result = await Swal.fire({
      icon: 'question',
      title,
      text: message,
      showCancelButton: true,
      confirmButtonColor: '#2E7D32',
      cancelButtonColor: '#616161',
      confirmButtonText: 'Confirmar',
      cancelButtonText: 'Cancelar',
    });
    return result.isConfirmed;
  }
}
