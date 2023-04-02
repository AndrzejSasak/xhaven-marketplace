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
    category?: CategoryEnum;
    images?: ImageDto[];
    postedDate?: string;
    price?: string;
}

export interface ImageDto {
    id?: string;
    imageName?: string;
}

export interface CategoryDto {
  categoryName?: string;
  subcategories?: CategoryDto[];
}
