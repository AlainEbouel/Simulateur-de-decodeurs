import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { AdminService } from '../../services/admin.service';
import { Client } from '../../models/client.model';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
  standalone: true,
  imports: [CommonModule, MatCardModule],
})
export class AdminPageComponent implements OnInit {
  clients: Client[] = [];
  availableDecoders: string[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getClients().subscribe(data => this.clients = data);
    this.adminService.getAvailableDecoders().subscribe(data => this.availableDecoders = data);
  }
}
