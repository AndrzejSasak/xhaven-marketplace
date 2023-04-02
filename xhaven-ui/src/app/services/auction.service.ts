import { Injectable } from '@angular/core';
import {AuctionDto} from "../models/dto/dto-models";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  url: string = 'http://localhost:8080/api/v1/auctions';

  constructor(private httpClient: HttpClient) { }

  getAuctions(): Observable<AuctionDto[]> {
    return this.httpClient.get<AuctionDto[]>(this.url);
  }
}
