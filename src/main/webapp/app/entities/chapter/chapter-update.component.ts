import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IChapter, Chapter } from 'app/shared/model/chapter.model';
import { ChapterService } from './chapter.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IBook } from 'app/shared/model/book.model';
import { BookService } from 'app/entities/book/book.service';

type SelectableEntity = IUser | IBook;

@Component({
  selector: 'jhi-chapter-update',
  templateUrl: './chapter-update.component.html',
})
export class ChapterUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  books: IBook[] = [];

  editForm = this.fb.group({
    id: [],
    title: [],
    userId: [],
    bookId: [],
  });

  constructor(
    protected chapterService: ChapterService,
    protected userService: UserService,
    protected bookService: BookService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chapter }) => {
      this.updateForm(chapter);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.bookService.query().subscribe((res: HttpResponse<IBook[]>) => (this.books = res.body || []));
    });
  }

  updateForm(chapter: IChapter): void {
    this.editForm.patchValue({
      id: chapter.id,
      title: chapter.title,
      userId: chapter.userId,
      bookId: chapter.bookId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chapter = this.createFromForm();
    if (chapter.id !== undefined) {
      this.subscribeToSaveResponse(this.chapterService.update(chapter));
    } else {
      this.subscribeToSaveResponse(this.chapterService.create(chapter));
    }
  }

  private createFromForm(): IChapter {
    return {
      ...new Chapter(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      bookId: this.editForm.get(['bookId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChapter>>): void {
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
