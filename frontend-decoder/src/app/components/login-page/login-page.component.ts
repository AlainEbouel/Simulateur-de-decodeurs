import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {UserCredentials} from '../../models/user-credentials';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {MatCardContent, MatCardModule, MatCardTitle} from '@angular/material/card';
import {SessionService} from '../../services/session.service';

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardContent,
    MatCardTitle,
    MatCardModule
  ],
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {
  credentials: UserCredentials = { username: '', password: '' };
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
    private router: Router
  ) {}

  onSubmit() {
    this.authService.checkRole(this.credentials).subscribe(role => {
      if (role) {
        this.sessionService.setSession(this.credentials, role);
        this.router.navigate([role === 'ADMIN' ? '/admin' : '/client']);
      } else {
        this.errorMessage = "Authentification échouée.";
      }
    });
  }

  private extractRoleFromResponse(body: any): string {
    // À adapter selon la réponse backend
    return 'ADMIN'; // ou 'CLIENT'
  }
}

