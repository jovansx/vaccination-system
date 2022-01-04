import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-document-card',
  templateUrl: './document-card.component.html',
  styleUrls: ['./document-card.component.scss']
})
export class DocumentCardComponent implements OnInit {

  @Input()
  dokument: any;
  constructor() { }

  ngOnInit(): void {
  }

}
