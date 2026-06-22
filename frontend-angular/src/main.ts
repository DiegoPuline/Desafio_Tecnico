import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { ListarCidades } from './app/listar-cidades';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

bootstrapApplication(ListarCidades, {
    providers: [
        provideAnimationsAsync(),
        provideHttpClient(),
    ],
}).catch((err) => console.error(err));