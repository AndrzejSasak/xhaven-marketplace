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

export interface OfferDto {
    title?: string;
    description?: string;
    category?: CategoryEnum;
    postedDate?: string;
    price?: string;
}