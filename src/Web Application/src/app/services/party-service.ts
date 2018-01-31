import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Globals } from '../app-properties';
import { Party } from '../models/party';

@Injectable()
export class PartyService{
    constructor (private http: Http, private globals: Globals) {}
    ENDPOINT = this.globals.endpoint + "/party";

    getParties() : Observable<Party[]> {
                 return this.http.get(this.ENDPOINT)
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getParty(id:string) : Observable<Party> {
                 return this.http.get(this.ENDPOINT + "/" + id)
                                 .map((res:Response) => res.json())                               
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    createParty(body: Object): Observable<Party> {
        let bodyString = JSON.stringify(body); 
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.post(this.ENDPOINT, body) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 

    updateParty (id:string,body: Object): Observable<Party> {
        let bodyString = JSON.stringify(body); 
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.put(this.ENDPOINT + "/" + id, body) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 
    
    deleteParty (id:string): Observable<Party> {
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.delete(this.ENDPOINT + "/" + id) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 
}