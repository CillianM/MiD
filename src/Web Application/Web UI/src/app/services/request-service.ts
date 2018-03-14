import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Globals } from '../app-properties';
import { IdentityType } from '../models/identitytype';
import { IdentityRequest } from '../models/request';

@Injectable()
export class RequestService{
    constructor (private http: Http, private globals: Globals) {}
    ENDPOINT = this.globals.endpoint + "/request";

    getRequestTypes() : Observable<string[]> {
                 return this.http.get(this.ENDPOINT + "/types")
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getRequest(senderId:string) : Observable<IdentityRequest> {
        return this.http.get(this.ENDPOINT + "/sender/" + senderId)
                        .map((res:Response) => res.json())                               
                        .catch((error:any) => Observable.throw(error.json().error || 'Server error'));

    }

    createRequest(body: Object): Observable<IdentityRequest> {
        let bodyString = JSON.stringify(body); 
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 

        return this.http.post(this.ENDPOINT, body) 
                         .map((res:Response) => res.json()) 
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    } 
}