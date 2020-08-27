import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.css']
})
export class ListUserComponent implements OnInit {

  constructor(private http: HttpClient) { }

  readonly ROOT = 'http://localhost:8080';
  ngOnInit(): void {
  }

  users: any;
  getUsers() {
    this.users = this.http.get(this.ROOT);
  }
}
