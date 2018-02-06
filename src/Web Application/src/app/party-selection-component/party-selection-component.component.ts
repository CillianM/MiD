import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { PartyService } from '../services/party-service';
import { Party } from '../models/party';
import { NgModule } from '@angular/core/src/metadata/ng_module';
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
  visible = false;
  visibleAnimate = false;

  show(): void {
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true, 100);
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
        this.hide();
      });
      err => console.log(err);
  }

  ngOnInit() {
    this.getParties();
  }

}


