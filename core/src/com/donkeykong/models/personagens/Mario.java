package com.donkeykong.models.personagens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.donkeykong.models.objetos.Deck;
import com.donkeykong.visao.StartGame;
import com.donkeykong.models.BitsDeColisao;
import com.donkeykong.models.Estado;

@SuppressWarnings("rawtypes")
public class Mario extends Sprite {
    //Constantes
    private static final float RUNNING_FRAME_DURATION = 0.25f;
    public World mundo;

    //Controle de condicoes
    private Estado estadoAtual;
    private boolean estouNaEscada = false, estouNoChao = true,
            transformado = false, estouMorto = false,
            olhandoParaADireita = true, estaComOMartelo = false;
    private final Deck deckDeVidas;

    //Texturas
    private TextureRegion marioParadoEsquerda;
    private TextureRegion marioParadoDireita;
    private TextureRegion marioEscalando;
    private TextureRegion marioPulandoEsquerda;
    private TextureRegion marioPulandoDireita;
    private TextureRegion marioFrame;

    //Animacoes
    private Animation<TextureRegion> andandoEsquerdaAnimacao;
    private Animation<TextureRegion> andandoDireitaAnimacao;
    private Animation<TextureRegion> marioMarteloDireita;
    private Animation<TextureRegion> marioMarteloEsquerda;
    float stateTime = 0f;

    //Componentes do personagem
    private final int posicaoX;
    private final int posicaoY; //posicoes iniciais
    public Body corpo;

    //Controle do tempo durante o update
    private float tempoPassado = 0f;

    //Sons
    private final Sound somAndando;
    private final Sound somPulando;
    private final Sound somMorrendo;

    public Mario(int posicaoX, int posicaoY, World mundo, Deck deckDeVidas) {
        super(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-01.png")), 80, 42);
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.mundo = mundo;
        this.deckDeVidas = deckDeVidas;

        //inicializando os sons
        somAndando = Gdx.audio.newSound(Gdx.files.internal("sons/walk.wav"));
        somPulando = Gdx.audio.newSound(Gdx.files.internal("sons/jump.wav"));
        somMorrendo = Gdx.audio.newSound(Gdx.files.internal("sons/die.wav"));

        //o Mario começa olhando para a direita
        estadoAtual = Estado.PARADO_DIREITA;

        criaCorpoMario();
        loadTextures();

        somAndando.loop();
    }

    //https://github.com/libgdx/libgdx/wiki/2D-Animation
    //https://www.javacodegeeks.com/2013/02/android-game-development-with-libgdx-animation-part-2.html
    private void loadTextures() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("personagens/marioAnimacao/marioAnimacao.txt"));
        TextureAtlas atlasMartelo = new TextureAtlas(Gdx.files.internal("personagens/marioMarteloAnimacao/marioMarteloAnimacao.txt"));

        marioParadoEsquerda = atlas.findRegion("Mario-01");
        marioParadoDireita = new TextureRegion(marioParadoEsquerda);
        marioParadoDireita.flip(true, false);
        marioEscalando = new TextureRegion(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-04.png")));
        marioPulandoEsquerda = new TextureRegion(new Texture(Gdx.files.internal("personagens/marioAnimacao/Mario-03.png")));
        marioPulandoDireita = new TextureRegion(marioPulandoEsquerda);
        marioPulandoDireita.flip(true, false);

        TextureRegion[] andandoEsquerdaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            andandoEsquerdaFrames[i] = atlas.findRegion("Mario-0" + (i + 1));
        }
        andandoEsquerdaAnimacao = new Animation<>(RUNNING_FRAME_DURATION, andandoEsquerdaFrames);

        TextureRegion[] andandoDireitaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            andandoDireitaFrames[i] = new TextureRegion(andandoEsquerdaFrames[i]);
            andandoDireitaFrames[i].flip(true, false);
        }
        andandoDireitaAnimacao = new Animation(RUNNING_FRAME_DURATION, andandoDireitaFrames);

        TextureRegion[] marteloEsquerdaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            marteloEsquerdaFrames[i] = atlasMartelo.findRegion("marioMartelo-0" + (i + 1));
        }
        marioMarteloEsquerda = new Animation<>(RUNNING_FRAME_DURATION, marteloEsquerdaFrames);

        TextureRegion[] marteloDireitaFrames = new TextureRegion[2];
        for (int i = 0; i < 2; i++) {
            marteloDireitaFrames[i] = new TextureRegion(marteloEsquerdaFrames[i]);
            marteloDireitaFrames[i].flip(true, false);
        }
        marioMarteloDireita = new Animation<>(RUNNING_FRAME_DURATION, marteloDireitaFrames);
    }

    //https://gamedev.stackexchange.com/questions/119143/how-to-fix-the-sprite-that-is-not-drawn-on-the-same-position-as-its-body-after-i
    public void criaCorpoMario() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL);
        corpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.filter.categoryBits = BitsDeColisao.MARIO;
        fdef.shape = shape;

        corpo.createFixture(fdef).setUserData("mario");
    }

    public void criaCorpoMarioMartelo() {
        Vector2 pos = corpo.getPosition();
        mundo.destroyBody(corpo);

        //bdef.position.set(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos);
        corpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.filter.categoryBits = BitsDeColisao.MARIO;
        fdef.shape = shape;

        corpo.createFixture(fdef).setUserData("marioComMartelo");
    }

    public void reCriaCorpoMario() {
        Vector2 pos = corpo.getPosition();
        mundo.destroyBody(corpo);

        //bdef.position.set(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(pos);
        corpo = mundo.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / StartGame.CONVERSAO_METRO_PIXEL);
        fdef.filter.categoryBits = BitsDeColisao.MARIO;
        fdef.shape = shape;

        corpo.createFixture(fdef).setUserData("mario");
    }

    public void mover(int direcao) {
        switch (direcao) {
            case 19:
                if (estouNaEscada) {
                    corpo.setLinearVelocity(new Vector2(0, 2f));
                }
                break;
            case 20:
                if (estouNaEscada) {
                    corpo.setLinearVelocity(new Vector2(0, -2f));
                }
                break;
            case 21:
                if ((corpo.getPosition().x) >= 25 / 40f) {
                    corpo.setLinearVelocity(new Vector2(-1.5f, 0));
                    olhandoParaADireita = false;
                }
                break;
            case 22:
                if ((corpo.getPosition().x) <= 672 / 40f) {
                    corpo.setLinearVelocity(new Vector2(1.5f, 0));
                    olhandoParaADireita = true;
                }
                break;
            case 62:
                if (corpo.getLinearVelocity().y == 0 && estouNoChao) {
                    if (olhandoParaADireita)
                        corpo.setLinearVelocity(new Vector2(1.5f, 4f));
                    else
                        corpo.setLinearVelocity(new Vector2(-1.5f, 4f));
                    estouNoChao = false;

                    somPulando.play();
                }
                break;
        }
    }

    public void render(float delta) {
        stateTime += delta; //guarda a passagem do tempo

        setPosition((corpo.getPosition().x) * StartGame.CONVERSAO_METRO_PIXEL,
                (corpo.getPosition().y) * StartGame.CONVERSAO_METRO_PIXEL);

        //de modo geral, a sequência das seguintes verificações condicionais não interfere no resultado

        if (estaComOMartelo) {
            criaCorpoMarioMartelo();
            estaComOMartelo = false; //se a variavel se mantivesse verdadeira, o jogo travaria, porque criaria o novo corpo infinitamente
            transformado = true; //usamos essa variavel para saber se ele ainda está com o martelo
        }

        //verifica se o mario levou dano
        if (estouMorto) {
            //volta para a posição inicial
            corpo.setTransform(posicaoX / StartGame.CONVERSAO_METRO_PIXEL, posicaoY / StartGame.CONVERSAO_METRO_PIXEL, 0);
            estouMorto = false; //reseta a variavel
        }

        //toca a música enquanto o mario estiver se movimentando
        if (corpo.getLinearVelocity().x > 0 || corpo.getLinearVelocity().x < 0) {
            somAndando.resume();
        } else {
            somAndando.pause();
        }

        //Sequencia de verificações para saber qual textura utilizar no momento

        //verifica se está se movimentando para os lados
        if (corpo.getLinearVelocity().x > 0 && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.CORRENDO_DIREITA;
        else if (corpo.getLinearVelocity().x < 0 && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.CORRENDO_ESQUERDA;

        //verifica para qual lado está olhando
        if (corpo.getLinearVelocity().x == 0 && olhandoParaADireita && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.PARADO_DIREITA;
        else if (corpo.getLinearVelocity().x == 0 && !olhandoParaADireita && corpo.getLinearVelocity().y == 0)
            estadoAtual = Estado.PARADO_ESQUERDA;

        //verifica se está pulando e se está pulando
        if (corpo.getLinearVelocity().y != 0 && !estouNaEscada && olhandoParaADireita)
            estadoAtual = Estado.PULANDO_DIREITA;
        else if (corpo.getLinearVelocity().y != 0 && !estouNaEscada && !olhandoParaADireita)
            estadoAtual = Estado.PULANDO_ESQUERDA;

        //verifica se está escalando
        if (corpo.getLinearVelocity().y != 0 && estouNaEscada)
            estadoAtual = Estado.ESCALANDO;

        //se o mario estiver transformado, essa parte é utilizada para saber quando ele deve voltar para o normal
        if (transformado) {
            tempoPassado += delta; //cada passagem é um segundo
            estadoAtual = olhandoParaADireita ? Estado.MARTELO_DIREITA : Estado.MARTELO_ESQUERDA;
            if (tempoPassado > 10f) { //10 segundos de duração do martelo
                reCriaCorpoMario();
                transformado = false;
            }
        }

        //Muda a sprite do MARIO de acordo com o resultado das verificações
        switch (estadoAtual) {
            case PARADO_DIREITA:
                marioFrame = marioParadoDireita;
                break;
            case PARADO_ESQUERDA:
                marioFrame = marioParadoEsquerda;
                break;
            case CORRENDO_DIREITA:
                marioFrame = andandoDireitaAnimacao.getKeyFrame(stateTime, true);
                break;
            case CORRENDO_ESQUERDA:
                marioFrame = andandoEsquerdaAnimacao.getKeyFrame(stateTime, true);
                break;
            case ESCALANDO:
                marioFrame = marioEscalando;
                break;
            case PULANDO_ESQUERDA:
                marioFrame = marioPulandoEsquerda;
                break;
            case PULANDO_DIREITA:
                marioFrame = marioPulandoDireita;
                break;
            case MARTELO_ESQUERDA:
                marioFrame = marioMarteloEsquerda.getKeyFrame(stateTime, true);
                break;
            case MARTELO_DIREITA:
                marioFrame = marioMarteloDireita.getKeyFrame(stateTime, true);
                break;
        }

        setRegion(marioFrame); //a nova textura
    }

    public boolean verificaColisao(Body body) { //verifica a colisão com o inimigo
        if (transformado) { //se estiver com o martelo, destroe o fogo
            body.setUserData("destruir"); //marca o inimigo para a destruição
            return true;
        } else {
            deckDeVidas.atualizarVida(); //diminui uma vida
            estouMorto = true;
            somMorrendo.play();
            return false;
        }
    }

    public void setEstaComOMartelo(boolean estaComOMartelo) {
        this.estaComOMartelo = estaComOMartelo;
    }

    public void setEstouNaEscada(boolean estouNaEscada) {
        this.estouNaEscada = estouNaEscada;
    }

    public void setEstouNoChao(boolean estouNoChao) {
        this.estouNoChao = estouNoChao;
    }
}
