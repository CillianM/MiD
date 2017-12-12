export class Submission{
    id:string;
    userId:string;
    partyId:string;
    data:string;
    status:string;
    date:string;
}

export enum SubmissionStatus{
    ACCEPTED = "ACCEPTED",
    REJECTED = "REJECTED"
}

export class Data {
    key:string;
    value:string;
}