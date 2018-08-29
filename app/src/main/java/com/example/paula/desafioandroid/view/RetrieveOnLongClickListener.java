package com.example.paula.desafioandroid.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.paula.desafioandroid.R;
import com.example.paula.desafioandroid.controller.ContatoController;
import com.example.paula.desafioandroid.model.Contato;
import com.example.paula.desafioandroid.view.MainActivity;

import static com.example.paula.desafioandroid.R.id.editTextContatoEmail;
import static com.example.paula.desafioandroid.R.id.editTextContatoNome;

public class RetrieveOnLongClickListener implements View.OnLongClickListener {

    Context context;
    String id;

    @Override
    public boolean onLongClick(View view) {

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] itens = {"Editar", "Deletar"};

        new AlertDialog.Builder(context).setTitle("Detalhes do Aluno")
                .setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            editContatoPeloID(Integer.parseInt(id));
                        } else if (item == 1) {
                            // deletar

                            boolean isDeletouComSucesso =
                                    new ContatoController(context).delete(Integer.parseInt(id));

                            if (isDeletouComSucesso) {
                                Toast.makeText(context, "Contato deletado.",
                                        Toast.LENGTH_SHORT).show();

                                ((MainActivity) context).contadorDeRegistros();
                                ((MainActivity) context).atualizarListaDeContatos();

                            } else {
                                Toast.makeText(context, "Erro ao deletar o contato.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        dialog.dismiss();

                    }
                }).show();

        return false;

    }

    public void editContatoPeloID(final int contatoID) {

        Toast.makeText(context,"Editando "+contatoID,Toast.LENGTH_SHORT).show();

        // Objetivo - Montar o formulário já com os dados populados

        // ContatoController
        final ContatoController contatoController =
                new ContatoController(context);


        // Contato via DB pela PK (contatoID)
        final Contato contato =
                contatoController.buscarContatoPeloID(contatoID);


        // Injetar o Layout contato_form
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View formContato =
                inflater.inflate(R.layout.contato_form,null,false);

        // Popular nome e email com dados da lista
        final EditText editTextNome = (EditText)
                formContato.findViewById(editTextContatoNome);
        final  EditText editTextEmail = (EditText)
                formContato.findViewById(editTextContatoEmail);

        editTextNome.setText(contato.getNome());
        editTextEmail.setText(contato.getEmail());

        // Show do formulário com dados populados

        new AlertDialog.Builder(context)
                .setView(formContato)
                .setTitle("Editar")
                .setPositiveButton("Atualizar dados",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {


                                Contato novoContato = new Contato();
                                novoContato.setId(contatoID);
                                novoContato.setNome(editTextNome.getText().toString());
                                novoContato.setEmail(editTextEmail.getText().toString());

                                boolean isUpdate = contatoController.update(novoContato);

                                if(isUpdate){

                                    Toast.makeText(context,"Dados atualizados com sucesso.",Toast.LENGTH_SHORT).show();

                                    ((MainActivity) context).atualizarListaDeContatos();

                                }else{
                                    Toast.makeText(context,"Falha ao Salvar Contato",Toast.LENGTH_SHORT).show();
                                }

                                dialog.cancel();

                            }

                        }).show();


        // Deixar o usuário alterar os dados

        // criar um novo OBJ Contato com os dados digitados
        // Salvar via Update da Controller
        // Atualizar view na Main Activity

    }
}

