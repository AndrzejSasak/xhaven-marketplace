import { Injectable } from '@angular/core';
import {AuctionDto, ThumbnailAuctionDto} from "../models/dto/dto-models";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {User} from "../models/user";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  API_URL: string = 'http://localhost:8080/api/v1/auctions';

  constructor(private httpClient: HttpClient) { }

  getAuctions(): Observable<ThumbnailAuctionDto[]> {
    const httpParams: HttpParams = new HttpParams().set('isActive', true);
    return this.httpClient.get<ThumbnailAuctionDto[]>(this.API_URL, {params: httpParams});
  }

  getAuctionsByOwnerId(id: string): Observable<ThumbnailAuctionDto[]> {
    const httpParams: HttpParams = new HttpParams().set('ownerId', id);
    return this.httpClient.get<ThumbnailAuctionDto[]>(`${this.API_URL}`, {params: httpParams});
  }

  getAuctionById(id: string | null): Observable<AuctionDto> {
    return this.httpClient.get<AuctionDto>(`${this.API_URL}/${id}`);
  }

  postNewAuction(formData: FormData): Observable<any> {
    return this.httpClient.post(this.API_URL, formData);
  }

  takeDownAuction(auctionId: string, isSold: boolean): Observable<any> {
    return this.httpClient.put(`${this.API_URL}/${auctionId}/status`, { isActive: false, isSold: isSold});
  }

}
