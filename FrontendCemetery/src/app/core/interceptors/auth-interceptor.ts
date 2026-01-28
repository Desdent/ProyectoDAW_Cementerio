import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');

  // Si la URL contiene 'auth/login' simplemente dejar pasdar la petición
  if (req.url.includes('/auth/login')) {
    return next(req);
  }

  // Clona la petición para añadir el encabezado
  const authReq = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  // Pasa la petición clonada al siguiente paso
  return next(authReq);
};
