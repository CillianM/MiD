import { Field } from "./field";

export class IdentityType{
    id:string;
    partyId:string;
    name:string;
    iconImg:string;
    coverImg:string;
    fields:Field[];
    versionNumber:number;
    status:string;
}