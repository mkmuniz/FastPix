export type StateType = {
    id: number;
    sigla: string;
    nome: string;
}

export type CityType = {
    id: number;
    nome: string;
    municipio: {
        microrregiao: {
            mesorregiao: {
                UF: {
                    sigla: string;
                }
            }
        }
    }
}

export type PixKeyType = 'email' | 'phone' | 'document' | '';

export type PixFormProps = {
    onQrCodeGenerated: (qrCode: { text: string; image: string } | null) => void;
}