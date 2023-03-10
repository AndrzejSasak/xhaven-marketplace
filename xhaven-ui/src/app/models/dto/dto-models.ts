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