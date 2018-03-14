import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IdentityTypeService } from '../services/identitytype-service';
import { IdentityType } from '../models/identitytype';
import { Field } from '../models/field';
import { Party } from '../models/party';
import { PartyService } from '../services/party-service';

@Component({
  selector: 'app-identity-type-view',
  templateUrl: './identity-type-view.component.html',
  styleUrls: ['./identity-type-view.component.css',
  '../app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class IdentityTypeViewComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private idenityTypeService: IdentityTypeService,
    private partyService:PartyService
  ){}

  partyId:string;
  identityId:string;
  identityType:IdentityType;
  deleteField:number[];
  party:Party;

  getIdentityTypes(){
    this.idenityTypeService.getIdentityType(this.identityId)
    .subscribe(
      identityType => {
        this.identityType = identityType,
        console.log(this.identityType)
      });
      err => console.log(err);
  }

  updateIdentityType(idenityType:IdentityType){
    this.idenityTypeService.updateIdentityType(this.identityId,idenityType)
    .subscribe(
      identityType => {
        console.log(this.identityType);
        this.router.navigateByUrl('/party/' + this.partyId);
      });
      err => console.log(err);
  }

  delete(index:number){
    if(!this.deleteField.includes(index)){
      this.deleteField.push(index)
    }
    else{
      this.deleteField.splice(this.deleteField.indexOf(index),1);
    }
  }

  deleteFields(){
    for(var i = this.deleteField.length-1; i >=0; i--){
      for(var j = this.identityType.fields.length-1; j >=0; j--){
        if(this.deleteField[i] == j){
          this.identityType.fields.splice(j,1);
          this.deleteField.splice(i,1);
        }
      }
    }
  }

  addField(){
    var field = new Field();
    field.type = "FIRSTNAME";
    this.identityType.fields.push(field);
  }

  updateName(index:number,name:string){
    this.identityType.fields[index].name = name;
  }

  updateType(index:number,type:string){
    this.identityType.fields[index].type = type;
  }

  deleteIdentity(){
    if(confirm("Are you sure to delete "+ this.identityType.name)) {
      this.idenityTypeService.deleteIdentityType(this.identityId,this.partyId)
    .subscribe(
      identityType => {
        this.router.navigateByUrl('/party/' + this.partyId);
      });
      err => console.log(err);
    }
  }

  submitData(name:string,coverImg:string,iconImg:string){
    var idenityType = new IdentityType();
    idenityType.partyId = this.partyId;
    idenityType.name = name;
    idenityType.iconImg = iconImg;
    idenityType.coverImg = coverImg;
    idenityType.fields = this.identityType.fields;
    console.log(idenityType);
    this.updateIdentityType(idenityType);
  }

  getParty(){
    this.partyService.getParty(this.partyId)
    .subscribe(
      party => {
        this.party = party,
        console.log(this.party)
      });
      err => console.log(err);
  }


  ngOnInit() {
    this.partyId = this.route.snapshot.paramMap.get('partyId');
    this.identityId = this.route.snapshot.paramMap.get('identityId');
    this.deleteField = [];
    this.getParty();
    this.getIdentityTypes();
  }

}
