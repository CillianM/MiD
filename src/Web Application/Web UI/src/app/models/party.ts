export class Party{
    id:string;
    name:string;
    status:string;
    keyId:string;
    publicKey:string;
}

export enum SubmissionStatus{
    ACCEPTED = "ACTIVE",
    REJECTED = "DELETED"
}