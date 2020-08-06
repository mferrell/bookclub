import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.BookclubBookModule),
      },
      {
        path: 'chapter',
        loadChildren: () => import('./chapter/chapter.module').then(m => m.BookclubChapterModule),
      },
      {
        path: 'topic',
        loadChildren: () => import('./topic/topic.module').then(m => m.BookclubTopicModule),
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.BookclubCommentModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BookclubEntityModule {}
