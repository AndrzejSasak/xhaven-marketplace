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
    contactInformation?: string;
    phoneNumber?: string;
    favorite: boolean;
}

export interface NewAuctionDto {
  title?: string;
  description?: string;
  category?: CategoryDto;
  price?: string;
  contactInformation?: string;
  phoneNumber?: string;
}

export interface ThumbnailAuctionDto {
  id?: string;
  title?: string;
  description?: string;
  price?: string;
  thumbnail?: ImageDto;
  postedAt: string;
  favorite: boolean;
}

export interface ImageDto {
    id?: string;
    imageName?: string;
    fileBytes: Blob;
}

export interface CategoryDto {
  id?: string;
  categoryName?: string;
  subcategories?: CategoryDto[];
}

export interface UserDto {
  id?: string;
  email?: string;
  name?: string;
  surname?: string;
  phoneNumber?: string;
}
