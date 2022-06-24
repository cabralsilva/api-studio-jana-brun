export enum SchoolLevelEnum {
  CHILD_EDUCATION_ONE = 'Educação infantil 1',
  CHILD_EDUCATION_TWO = 'Educação infantil 2',
  CHILD_EDUCATION_THREE = 'Educação infantil 3',
  PRE_SCHOOL_ONE = 'Pré-escola 1',
  PRE_SCHOOL_TWO = 'Pré-escola 2',
  PRE_SCHOOL_THREE = 'Pré-escola 3',
  FUNDAMENTAL_ONE = 'Fundamental 1',
  FUNDAMENTAL_TWO = 'Fundamental 2',
  FUNDAMENTAL_THREE = 'Fundamental 3',
  FUNDAMENTAL_FOUR = 'Fundamental 4',
  FUNDAMENTAL_FIVE = 'Fundamental 5',
  FUNDAMENTAL_SIX = 'Fundamental 6',
  FUNDAMENTAL_SEVEN = 'Fundamental 7',
  FUNDAMENTAL_EIGHT = 'Fundamental 8',
  FUNDAMENTAL_NINE = 'Fundamental 9'
}

export const SchoolLevelList: {
    key: string;
    value: string;
}[] = Object.entries(SchoolLevelEnum).map(([key, value]) => ({ key, value }))

export type SchoolLevelType = keyof typeof SchoolLevelEnum

export const SchoolLevelEntries = Object.entries(SchoolLevelEnum)
