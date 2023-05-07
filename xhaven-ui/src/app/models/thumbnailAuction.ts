import {SafeUrl} from "@angular/platform-browser";

export interface ThumbnailAuction {
  id?: string;
  title?: string;
  description?: string;
  price?: string;
  thumbnail?: SafeUrl;
  postedAt?: string;
  favorite: boolean;
}
