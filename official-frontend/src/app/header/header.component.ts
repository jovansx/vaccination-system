import { Component, OnInit } from '@angular/core';
import { AuthService } from '../autentification/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private _auth: AuthService) {}

  ngOnInit(): void {
  }

  logout(): void {
    this._auth.logoutUser();
  }

}
