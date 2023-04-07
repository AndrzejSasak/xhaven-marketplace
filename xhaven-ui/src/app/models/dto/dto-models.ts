import { CategoryEnum } from "../category";

export interface LoginDto {
    email?: string;
    password?: string;
}

export interface RegisterDto {
    name?: string;
    surname?: string;
    email?: string;
    phoneNumber?: string;
    password?: string;
}

export interface TokenDto {
    token?: string;
}

export interface AuctionDto {
    id?: string;
    title?: string;
    description?: string;
    categoryId?: string;
    images?: ImageDto[];
    postedDate?: string;
    price?: string;
    contactInformation?: string,
    phoneNumber?: string
}

export interface NewAuctionDto {
  title?: string,
  description?: string,
  category?: CategoryDto,
  price?: string,
  contactInformation?: string,
  phoneNumber?: string
}

export interface ImageDto {
    id?: string;
    imagePath?: string;
    file?: FormData;
}

export interface CategoryDto {
  id?: string;
  categoryName?: string;
  subcategories?: CategoryDto[];
}
