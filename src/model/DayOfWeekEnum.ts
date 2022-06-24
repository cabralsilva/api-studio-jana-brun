export enum DayOfWeekEnum {
  MONDAY = 'Segunda-feira',
  TUESDAY = 'Terça-feira',
  WEDNESDAY = 'Quarta-feira',
  THURSDAY = 'Quinta-feira',
  FRIDAY = 'Sexta-feira',
  SATURDAY = 'Sábado',
  SUNDAY = 'Domingo'
}

export const DayOfWeekList: {
    key: string;
    value: string;
}[] = Object.entries(DayOfWeekEnum).map(([key, value]) => ({ key, value }))

export type DayOfWeekType = keyof typeof DayOfWeekEnum

export const DayOfWeekEntries = Object.entries(DayOfWeekEnum)
