import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { PartyService } from '../services/party-service';
import { Party } from '../models/party';
import { NgModule } from '@angular/core/src/metadata/ng_module';
import { ModalService } from '../modal-window/modal.service';
import { Globals } from '../app-properties';

@Component({
  selector: 'app-party-selection-component',
  templateUrl: './party-selection-component.component.html',
  styleUrls: [
    './party-selection-component.component.css',
    '../app.component.css'
  ],
  encapsulation: ViewEncapsulation.None
})
export class PartySelectionComponentComponent implements OnInit {

  constructor(private partyService: PartyService,private globals: Globals) { }

  parties: Party[];

  getParties(){
    this.partyService.getParties()
    .subscribe(
      parties => {
        this.parties = parties, 
        console.log(this.parties)
      });
      err => console.log(err);
  }

  createNewParty(name: string){
    var newParty = new Party();
    newParty.name = name;
    newParty.publicKey = this.globals.publicKey;
    console.log(newParty);

    this.partyService.createParty(newParty)
    .subscribe(
      returnedParty => {
        this.parties.push(returnedParty), 
        console.log(returnedParty)
      });
      err => console.log(err);
  }

  ngOnInit() {
    this.getParties();
  }

}


