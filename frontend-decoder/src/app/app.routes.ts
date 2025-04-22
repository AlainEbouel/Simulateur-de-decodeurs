import { Routes } from '@angular/router';
import {LoginPageComponent} from './components/login-page/login-page.component';
import {TestViewComponent} from './components/test-view/test-view.component';
import {ClientPageComponent} from './components/client-page/client-page.component';
import {AdminPageComponent} from './components/admin-page/admin-page.component';
import {AdminGuard} from './guards/admin.guard';
import {ClientGuard} from './guards/client.guard';

export const routes: Routes = [
  { path: '', component: LoginPageComponent },
  {
    path: 'admin',
    component: AdminPageComponent,
    canActivate: [AdminGuard]
  },
  {
    path: 'client',
    component: ClientPageComponent,
    canActivate: [ClientGuard]
  }
];
