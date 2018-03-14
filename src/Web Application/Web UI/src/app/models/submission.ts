interface Serializable<T> {
    deserialize(input: Object): T;
}

export class Submission{
    id:string;
    userId:string;
    partyId:string;
    data:string;
    status:string;
    date:string;
}

export class SubmissionData {
    imageData:string;
    cardFields:CardField[];
}

export class CardField {
    fieldEntry: String;
    fieldType: String;
    fieldTitle: String;
    dataIcon: number;
}

export class Data {
    key:string;
    value:string;
}

export enum SubmissionStatus{
    ACCEPTED = "ACCEPTED",
    REJECTED = "REJECTED"
}