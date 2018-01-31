export class Party{
    id:string;
    name:string;
    status:string;
}

export enum SubmissionStatus{
    ACCEPTED = "ACTIVE",
    REJECTED = "DELETED"
}