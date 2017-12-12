import { Injectable }     from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import { Submission } from '../models/submission';

@Injectable()
export class SubmissionService{
    constructor (private http: Http) {}
    ENDPOINT = "http://localhost:8080/submission";
    PARTYID = "test";

    getSubmissions() : Observable<Submission[]> {
        
                 // ...using get request
                 return this.http.get(this.ENDPOINT + "/party/" + this.PARTYID)
                                // ...and calling .json() on the response to return data
                                 .map((res:Response) => res.json())
                                 //...errors if any
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    getSubmission(id:string) : Observable<Submission> {
        
                 // ...using get request
                 return this.http.get(this.ENDPOINT + "/" + id)
                                // ...and calling .json() on the response to return data
                                 .map((res:Response) => res.json())
                                 //...errors if any
                                 .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
        
    }

    updateSubmission (id:string,body: Object): Observable<Submission> {
        let bodyString = JSON.stringify(body); // Stringify payload
        let headers      = new Headers({ 'Content-Type': 'application/json' }); // ... Set content type to JSON
        let options       = new RequestOptions({ headers: headers }); // Create a request option

        return this.http.put(this.ENDPOINT + "/" + id, body) // ...using post request
                         .map((res:Response) => res.json()) // ...and calling .json() on the response to return data
                         .catch((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
    }   
}