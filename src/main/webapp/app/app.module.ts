import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BookclubSharedModule } from 'app/shared/shared.module';
import { BookclubCoreModule } from 'app/core/core.module';
import { BookclubAppRoutingModule } from './app-routing.module';
import { BookclubHomeModule } from './home/home.module';
import { BookclubEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    BookclubSharedModule,
    BookclubCoreModule,
    BookclubHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BookclubEntityModule,
    BookclubAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class BookclubAppModule {}
