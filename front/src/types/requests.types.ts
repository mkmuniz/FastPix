export type CreatePixRequestTypes = {
    value: number;
    name: string;
    pixKey: string;
    state: string;
    city: string;
}

export type PixResponseTypes = {
    id: string;
    value: number;
    name: string;
    status: string;
    createdAt: string;
    qrCodeText: string;
    qrCodeImage: string;
}

export type ApiErrorResponseTypes = {
    message: string;
}