import { Routes } from '@angular/router';
import {LoginPageComponent} from './components/login-page/login-page.component';
import {TestViewComponent} from './components/test-view/test-view.component';
import {ClientPageComponent} from './components/client-page/client-page.component';
import {AdminPageComponent} from './components/admin-page/admin-page.component';

export const routes: Routes = [
  { path: '', component: LoginPageComponent },
  { path: 'admin', component: AdminPageComponent },
  { path: 'client', component: ClientPageComponent }
];
