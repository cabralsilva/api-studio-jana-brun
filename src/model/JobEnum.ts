export enum JobEnum {
    DIRECTOR = 'Diretor(a)',
    FINANCIAL = 'Financeiro',
    SECRETARY = 'Secretária',
    TEACHER = 'Professor(a)'
}

export const JobList: {
    key: string;
    value: string;
}[] = Object.entries(JobEnum).map(([key, value]) => ({ key, value }))

export type JobType = keyof typeof JobEnum
