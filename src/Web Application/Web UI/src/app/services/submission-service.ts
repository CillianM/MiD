import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Submission } from '../models/submission';
import { Globals } from '../app-properties';

@Injectable()
export class SubmissionService{
    constructor (private http: Http, private globals: Globals) {}
    ENDPOINT = this.globals.endpoint + "/submission";

    getSubmissions(partyId:string) : Observable<Submission[]> {
                 return this.http.get(this.ENDPOINT + "/party/" + partyId)
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getSubmission(id:string,partyId:Object) : Observable<Submission> {
                 return this.http.get(this.ENDPOINT + "/" + id + "?partyId=" + partyId,)
                                 .map((res:Response) => res.json())
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    updateSubmission (id:string,body: Object): Observable<Submission> {
        let bodyString = JSON.stringify(body);
        let headers      = new Headers({ 'Content-Type': 'application/json' }); 
        let options       = new RequestOptions({ headers: headers }); 
        return this.http.put(this.ENDPOINT + "/" + id, body) 
                         .map((res:Response) => res.json())
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); 
    }   
}