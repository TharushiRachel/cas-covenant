export interface AFAnnexureDTO {
    annexureId: number;
    name: string;
    description: string;
    isMandatory: 'Y' | 'N';
    createdDate: string;
    createdBy: string;
    modifiedDate: string;
    modifiedBy: string;
    questions: AFAnnexureQuestionDTO[];
  }  
  
  export interface AFAnnexureQuestionDTO {
    questionId: number;
    annexureId: number;
    question: string;
    answerType: 'MCQ_WITH_SINGLE' | 'MCQ_WITH_MULTIPLE' | 'DESCRIPTIVE' | 'DESCRIPTIVE_WITH_SUB_QUESTION';
    createdDate: string;
    createdBy: string;
    modifiedDate: string;
    modifiedBy: string;
    displayOrder: number;
    isMandatory: 'Y' | 'N';
    answers: AFAnnexureAnswerDTO[];
    userAnswer?: string | null; 
    answerData?: string | null; 
  }
  
  
  export interface AFAnnexureAnswerDTO {
    answerId: number;
    questionId: number;
    selectionType: 'S' | 'M'; // S = Single, M = Multiple (select options)
    answer: string;
    displayOrder: number;
    createdDate: string | null;
    createdBy: string;
    modifiedDate: string;
    modifiedBy: string;
    isSelected:boolean;
    userAnswer:string
    
  }
  