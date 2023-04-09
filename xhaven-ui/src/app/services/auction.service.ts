import { Injectable } from '@angular/core';
import {AuctionDto} from "../models/dto/dto-models";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {User} from "../models/user";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuctionService {

  url: string = 'http://localhost:8080/api/v1/auctions';
  currentUser: User;

  constructor(private httpClient: HttpClient,
              private userService: UserService) { }

  getAuctions(): Observable<AuctionDto[]> {
    return this.httpClient.get<AuctionDto[]>(this.url);
  }

  getAuctionsByUserId(id: string): Observable<AuctionDto[]> {
    console.log(id);
    let requestParams = new HttpParams();
    requestParams = requestParams.append('userId', id);
    return this.httpClient.get<AuctionDto[]>(this.url, {params: requestParams});
  }

  postNewAuction(formData: FormData): Observable<any> {
    return this.httpClient.post(this.url, formData);
  }

}
