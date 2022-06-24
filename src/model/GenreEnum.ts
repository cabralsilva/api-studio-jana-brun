export enum GenreEnum {
    MALE = 'Masculino',
    FEMALE = 'Feminino'
}

export const GenreList: {
    key: string;
    value: string;
}[] = Object.entries(GenreEnum).map(([key, value]) => ({ key, value }))
