import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Globals } from '../app-properties';
import { IdentityType } from '../models/identitytype';

@Injectable()
export class IdentityTypeService{
    constructor (private http: Http, private globals: Globals) {}
    ENDPOINT = this.globals.endpoint + "/identitytype";

    getIdentityTypes() : Observable<IdentityType[]> {
                 return this.http.get(this.ENDPOINT)
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getPartyIdentityTypes(partyId:string) : Observable<IdentityType[]> {
                 return this.http.get(this.ENDPOINT + "/" + partyId)
                                 .map((res:Response) => res.json())                               
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getIdentityType(partyId:string,id:string) : Observable<IdentityType[]> {
        return this.http.get(this.ENDPOINT + "/" + partyId + "/" + id)
                        .map((res:Response) => res.json())                               
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));

    }

    createIdentityType(body: Object): Observable<IdentityType> {
        let bodyString = JSON.stringify(body); 
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.post(this.ENDPOINT, body) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 

    updateIdentityType (partyId:string,id:string,body: Object): Observable<IdentityType> {
        let bodyString = JSON.stringify(body); 
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.put(this.ENDPOINT + "/" + partyId + "/" + id, body) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 
    
    deleteIdentityType (partyId:string,id:string): Observable<IdentityType> {
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.delete(this.ENDPOINT + "/" + partyId + "/" + id) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 
}