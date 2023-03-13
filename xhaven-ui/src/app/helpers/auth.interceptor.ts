import { Injectable } from '@angular/core';

import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor
  } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

@Injectable()
export class TokenInteceptor implements HttpInterceptor {

constructor(private authService: AuthService) {}

intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    
    if(req.headers.has('skipIntercept')) {
        req = req.clone({
            headers: req.headers.delete('skipIntercept')
        })

        return next.handle(req);
    }

    req = req.clone({
        setHeaders: {
            Authorization: `Bearer ${this.authService.getToken()}`
        }
    });

    return next.handle(req);
}

}