import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { RequestService } from '../services/request-service';
import { IdentityTypeService } from '../services/identitytype-service';
import { IdentityType } from '../models/identitytype';
import { IdentityRequest } from '../models/request';
import { PartyService } from '../services/party-service';
import { Party } from '../models/party';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

  constructor(private requestService:RequestService,
    private identityTypeService:IdentityTypeService,
    private partyService:PartyService
  ) { }

  visible = false;
  visibleAnimate = false;
  visibleId = false;
  visibleAnimateId = false;
  visibleCreate = false;
  visibleAnimateCreate = false;
  mid:string;
  parties:Party[];
  requestTypes:string[];
  viewableRequestTypes:string[];
  request:IdentityRequest;
  identityTypes:IdentityType[];
  selectedType = 0;
  selectedParty = 0;
  updated = false;
  selectedTypes:boolean[];

  updateSelectedType(i:number){
    this.selectedType = i;
  }

  updateParty(i:number){
    this.selectedParty = i;
  }
  
  updateSelectedTypes(i:number){
    this.selectedTypes[i] = !this.selectedTypes[i];
  }

  useIdentityType(){
    this.viewableRequestTypes = this.requestTypes.slice();
    if(!this.updated){
      for(let i = this.viewableRequestTypes.length - 1; i >= 0; i--){
        if(this.notInIdentity(this.viewableRequestTypes[i])){
          this.viewableRequestTypes.splice(i,1);
        }
      }
      this.updated = true;
    } else{
      this.updated = false;
    }
    this.setClicked();
  }

  setClicked(){
    this.selectedTypes = new Array();
    for(let i = this.viewableRequestTypes.length - 1; i >= 0; i--){
      this.selectedTypes.push(false);
    }
  }

  notInIdentity(type:string){
    let identity = this.identityTypes[this.selectedType];
    for(let i = 0; i < identity.fields.length; i++){
      if(identity.fields[i].type == type){
        return false;
      }
    }
    return true;
  }

  submitRequest(){
    let types = this.getSelectedTypes()
    let newRequest = new IdentityRequest();
    newRequest.identityTypeFields = types;
    if(this.updated){
      newRequest.indentityTypeId = this.identityTypes[this.selectedType].id;
    }
    newRequest.recipientId = this.mid;
    newRequest.senderId = this.parties[this.selectedParty].id;
    console.log(newRequest);
    this.createRequest(newRequest);
  }

  getParties(){
    this.partyService.getParties()
    .subscribe(
      parties => {
        this.parties = parties, 
        console.log(this.parties)
      });
      err => console.log(err);
  }

  getSelectedTypes(){
    let string = "";
    for(let i = 0; i < this.selectedTypes.length; i++){
      if(this.selectedTypes[i]){
        string += this.viewableRequestTypes[i] + ",";
      }
    }
    return string = string.substring(0,string.length-1);
  }

  showCreate(mid:string): void {
    this.mid = mid;
    this.visibleCreate = true;
    setTimeout(() => this.visibleAnimateCreate = true, 100);
    this.visibleAnimateId = false;
    setTimeout(() => this.visibleId = false, 300);
  }

  hideCreate(): void {
    this.visibleAnimateCreate = false;
    setTimeout(() => this.visibleCreate = false, 300);
  }

  show(): void {
    this.hideCreate();
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true, 100);
    this.visibleId = true;
    setTimeout(() => this.visibleAnimateId = true, 100);
  }

  hide(): void {
    this.visibleAnimate = false;
    setTimeout(() => this.visible = false, 300);
  }

  onContainerClicked(event: MouseEvent): void {
    if ((<HTMLElement>event.target).classList.contains('modal')) {
      this.hide();
    }
  }

  getIdentityTypes(){
    this.identityTypeService.getIdentityTypes()
    .subscribe(
      identityTypes => {
        this.identityTypes = identityTypes,
        console.log(this.identityTypes)
      });
      err => console.log(err);
  }

  getRequestTypes(){
    this.requestService.getRequestTypes()
    .subscribe(
      requestTypes => {
        this.requestTypes = requestTypes;
        this.viewableRequestTypes = this.requestTypes.slice();
        this.setClicked();
        console.log(this.requestTypes);
      });
      err => console.log(err);
  }

  getRequest(id:string){
    this.requestService.getRequest(id)
    .subscribe(
      request => {
        this.request = request,
        console.log(this.request)
      });
      err => console.log(err);
  }

  createRequest(request:IdentityRequest){
    this.requestService.createRequest(request)
    .subscribe(
      request => {
        this.request = request,
        console.log(this.request)
      });
      err => console.log(err);
  }

  ngOnInit() {
    this.getRequestTypes();
    this.getIdentityTypes();
    this.getParties();
  }

}
