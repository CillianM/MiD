import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IdentityTypeService } from '../services/identitytype-service';
import { IdentityType } from '../models/identitytype';
import { Field } from '../models/field';

@Component({
  selector: 'app-identity-type-list',
  templateUrl: './identity-type-list.component.html',
  styleUrls: ['./identity-type-list.component.css',
  '../app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class IdentityTypeListComponent implements OnInit {

  constructor(private route: ActivatedRoute,private router: Router,private idenityTypeService: IdentityTypeService) { }

  partyId:string;
  idenityTypes:IdentityType[]
  newFields:Field[];
  deleteField:number[];

  getIdentityTypes(){
    this.idenityTypeService.getPartyIdentityTypes(this.partyId)
    .subscribe(
      idenityTypes => {
        this.idenityTypes = idenityTypes,
        console.log(this.idenityTypes)
      });
      err => console.log(err);
  }

  createIdentityType(idenityType:IdentityType){
    this.idenityTypeService.createIdentityType(idenityType)
    .subscribe(
      idenityType => {
        this.idenityTypes.push(idenityType),
        console.log(this.idenityTypes)
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
      for(var j = this.newFields.length-1; j >=0; j--){
        if(this.deleteField[i] == j){
          this.newFields.splice(j,1);
          this.deleteField.splice(i,1);
        }
      }
    }
  }

  addField(){
    var field = new Field();
    field.type = "FIRSTNAME";
    this.newFields.push(field);
  }

  updateName(index:number,name:string){
    this.newFields[index].name = name;
  }

  updateType(index:number,type:string){
    this.newFields[index].type = type;
  }

  submitData(name:string,coverImg:string,iconImg:string){
    var idenityType = new IdentityType();
    idenityType.partyId = this.partyId;
    idenityType.name = name;
    idenityType.iconImg = iconImg;
    idenityType.coverImg = coverImg;
    idenityType.fields = this.newFields;
    console.log(idenityType);
    this.createIdentityType(idenityType);
  }

  ngOnInit() {
    this.partyId = this.route.snapshot.paramMap.get('partyId');
    this.newFields = [];
    this.deleteField = [];
    this.getIdentityTypes()
  }

}
