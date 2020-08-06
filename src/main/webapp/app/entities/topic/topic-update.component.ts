import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITopic, Topic } from 'app/shared/model/topic.model';
import { TopicService } from './topic.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';
import { IChapter } from 'app/shared/model/chapter.model';
import { ChapterService } from 'app/entities/chapter/chapter.service';

type SelectableEntity = IUser | IBook | IChapter;

@Component({
  selector: 'jhi-topic-update',
  templateUrl: './topic-update.component.html',
})
export class TopicUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  books: IBook[] = [];
  chapters: IChapter[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    userId: [],
    bookId: [],
    chapterId: [],
  });

  constructor(
    protected topicService: TopicService,
    protected userService: UserService,
    protected bookService: BookService,
    protected chapterService: ChapterService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ topic }) => {
      this.updateForm(topic);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));

      this.chapterService.query().subscribe((res: HttpResponse<IChapter[]>) => (this.chapters = res.body || []));
    });
  }

  updateForm(topic: ITopic): void {
    this.editForm.patchValue({
      id: topic.id,
      title: topic.title,
      userId: topic.userId,
      bookId: topic.bookId,
      chapterId: topic.chapterId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const topic = this.createFromForm();
    if (topic.id !== undefined) {
      this.subscribeToSaveResponse(this.topicService.update(topic));
    } else {
      this.subscribeToSaveResponse(this.topicService.create(topic));
    }
  }

  private createFromForm(): ITopic {
    return {
      ...new Topic(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
      chapterId: this.editForm.get(['chapterId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITopic>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
