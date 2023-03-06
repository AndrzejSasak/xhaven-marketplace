import { RoleEnum } from "./role";

export interface User {
    id?: string;
    email?: string;
    password?: string;
    firstname?: string;
    lastname?: string;
    phoneNumber?: string;
    role?: RoleEnum[];
    token?: string;
}