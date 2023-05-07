import {Injectable} from '@angular/core';
import {User} from "../models/user";
import {Observable, of, tap} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {ThumbnailAuctionDto} from "../models/dto/dto-models";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  API_URL = 'http://localhost:8080/api/v1/users';
  private currentUser: User | null;
  constructor(private httpClient: HttpClient) {}

  getCurrentUser() {
    return this.currentUser ? of(this.currentUser) : this.httpClient.get<User>(`${this.API_URL}/current`)
      .pipe(tap((user: User) => {
        this.currentUser = user;
        return user;
      }));
  }

  removeCurrentUser() {
    this.currentUser = null;
  }

  getFavoriteAuctionsOfUser(userId: string | undefined): Observable<ThumbnailAuctionDto[]> {
    return this.httpClient.get<ThumbnailAuctionDto[]>(`${this.API_URL}/${userId}/auctions/favorites`);
  }

  addAuctionToFavorites(auctionId: string, userId: string): Observable<any> {
    return this.httpClient.post(`${this.API_URL}/${userId}/auctions/favorites/${auctionId}`, {});
  }

  removeAuctionFromFavorites(auctionId: string, userId: string): Observable<any> {
    return this.httpClient.delete(`${this.API_URL}/${userId}/auctions/favorites/${auctionId}`);
  }

}
