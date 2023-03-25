import { Injectable } from '@angular/core';
import {OfferDto} from "../models/dto/dto-models";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  url: string = 'http://localhost:8080/api/v1/offers';

  constructor(private httpClient: HttpClient) { }

  getOffers(): Observable<OfferDto[]> {
    return this.httpClient.get<OfferDto[]>(this.url);
  }
}
