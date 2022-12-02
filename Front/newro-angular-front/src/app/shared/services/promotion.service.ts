import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Promotion } from '../model/promotion.model';
import { BACK_END_URL } from './back-end-url';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  constructor(private http: HttpClient) { }
  token = sessionStorage.getItem("token");
  getPromotions(): Observable<Promotion[]> {
    return this.http.get<Promotion[]>(BACK_END_URL + '/promotions',{headers : {"Authorization" : `Bearer ${this.token}` }});
  }
}
